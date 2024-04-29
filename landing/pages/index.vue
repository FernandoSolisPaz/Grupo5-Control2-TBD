<script setup>

import { format, isWithinInterval, addDays  } from 'date-fns'

// Theming
const colorMode = useColorMode();

const isDark = computed({
  get () {
    return colorMode.value === 'dark'
  },
  set () {
    colorMode.preference = colorMode.value === 'dark' ? 'light' : 'dark'
  }
});

// Dialog
const isOpen = ref(false);

const date = ref(new Date());

const user = userStore();
const router = useRouter();
const tokenCookie = useCookie('token');
if (!tokenCookie.value) {
    router.push('/login');
}

const tasks = ref([]);

async function getTasks() {
    const response = await useApi().getTasksById(user.getUserId(), tokenCookie.value);
    tasks.value = response.map(task => ({ ...task, isOpen: false, dateObj: textDateToDate(task.date_of_expire) }));
}

const taskLength = computed(() => tasks.value.length);

const title = ref('');

async function addTask() {
    if (!title.value) return;

    const newTask = {
        title: title.value,
        description: '',
        state: 'Pending',
        date_of_expire: format(date.value, 'dd-MM-yyyy'),
        dateObj: textDateToDate(format(date.value, 'dd-MM-yyyy')),
    }
    await useApi().addTask(user.getUserId(), newTask, tokenCookie.value);
    tasks.value.push({...newTask, isOpen: false });
    title.value = '';
    date.value = new Date();
}

function textDateToLabel(date) {
    const newDate = new Date(date+'T00:00:00');
    return format(newDate, 'd MMM, yyy');
}

function textDateToDate(date) {
    return new Date(date+'T00:00:00');
}

function deleteTask(task) {
    tasks.value = tasks.value.filter(t => t.task_id !== task.task_id);
}

// Paginación y filtrado
const currentPage = ref(1);
const tasksPerPage = ref(5);
const filter = ref('');
const filterStatus = ref('All');

const filteredTasks = computed(() => {
  return tasks.value.filter(task => {
    if (filterStatus.value === 'All') {
      return task.title.toLowerCase().includes(filter.value.toLowerCase());
    } else {
      return task.title.toLowerCase().includes(filter.value.toLowerCase()) && task.state === filterStatus.value;
    }
  }).slice((currentPage.value - 1) * tasksPerPage.value, currentPage.value * tasksPerPage.value);
});

const filteredTaskCount = computed(() => {
  return tasks.value.filter(task =>
    task.title.toLowerCase().includes(filter.value.toLowerCase())
  ).length;
});

function toggleTaskState(task) {
    task.state = task.state === 'Completed' ? 'Pending' : 'Completed';
}

// Notificaciones
const toast = useToast();
function checkTasksExpiringSoon() {
    const today = new Date();
    const twoDaysFromNow = addDays(today, 2);

    const expiringTasks = tasks.value.filter(task =>
        isWithinInterval(task.dateObj, { start: today, end: twoDaysFromNow })
    );
    if (expiringTasks.length > 0) {
        expiringTasks.forEach(task => {
            toast.add({ title:`La tarea ${task.title} expirará pronto`, color:'green', timeout:0, icon:'i-heroicons-bell-alert-16-solid'});
        });
    }
}

onMounted(() => {
    getTasks();
    checkTasksExpiringSoon();
});

</script>

<template>
    <div class="flex justify-center items-center content-below-appbar">
        <UCard class="w-screen mx-52 min-w-96 ring-0">
            <h1 class="font-bold text-primary text-3xl mx-10 py-4 mb-4">Tareas</h1>
            <div class="flex items-center justify-between">
                <UInput v-model="filter" class="mx-10 my-4" size="md" icon="i-heroicons-magnifying-glass" color="primary" placeholder="Buscar una tarea"/>
                <div class="mx-10 my-4">
                    <UButton @click="filterStatus = 'All'" :variant="filterStatus === 'All' ? 'solid' : 'ghost'">Todas</UButton>
                    <UButton class="m-4" @click="filterStatus = 'Completed'" :variant="filterStatus === 'Completed' ? 'solid' : 'ghost'">Completadas</UButton>
                    <UButton @click="filterStatus = 'Pending'" :variant="filterStatus === 'Pending' ? 'solid' : 'ghost'">Pendientes</UButton>
                </div>
            </div>

            <div v-if="taskLength > 0" v-for="task in filteredTasks" :key="task.task_id" class="flex px-10 items-center">
                <UModal v-model="task.isOpen">
                    <UCard>
                        <UForm>
                            <UFormGroup name="title" size="xl" outline class="font-bold my-4 mx-4">
                                <UInput v-model="task.title"  />
                            </UFormGroup>

                            <UFormGroup name="description" outline class="mb-4 mx-4">
                                <UInput v-model="task.description" />
                            </UFormGroup>

                            <UFormGroup class="font-bold mx-4">
                                    <UPopover :popper="{ placement: 'top-end' }">
                                        <UButton size="sm" variant="ghost" icon="i-heroicons-calendar-days-20-solid" :label="format(task.dateObj, 'd MMM, yyy')" />
                                        
                                        <template #panel="{ close }">
                                            <client-only>
                                                <VDatePicker :is-dark="isDark" color="green" v-model="task.dateObj" is-required @close="close"/>
                                            </client-only>
                                        </template>
                                    </UPopover>
                            </UFormGroup>
                        </UForm>

                    </UCard>
                </UModal>
                <UCard class="w-full my-2">
                    <div class="flex items-center justify-between">
                        <div class="flex items-center">
                            <UCheckbox :checked="task.state === 'Completed'" @change="toggleTaskState(task)" class="text-primary py-2"/>
                            <p class="text-primary font-medium px-4 hover:text-blue-500 cursor-pointer" @click="task.isOpen = true">{{ task.title }}</p>
                        </div>
                        <div class="flex items-center">
                            <p class="text-primary font-medium mr-2">{{ format(task.dateObj, 'd MMM, yyy') }}</p>
                            <UButton class="delete" variant="link" color="red" icon="i-heroicons-trash" @click="deleteTask(task)" />
                        </div>
                    </div>
                    </UCard>
            </div>

            <div v-else class="text-center">
                <p class="text-primary mx-10 py-2 font-medium">Intenta añadiendo una nueva tarea</p>
                <UIcon name="i-heroicons-arrow-down" class="w-8 h-8 text-primary mt-2"/>
            </div>
            <UPagination :total="filteredTaskCount" :per-page="filteredTaskCount" v-model="currentPage" class="my-4 mx-10 text-center"/>
            <div class="flex items-center mx-10 py-4 mt-4 justify-between">

                <UInput v-model="title" size="xl" class="w-full" icon="i-heroicons-plus-circle" color="primary" placeholder="Agregar una tarea" :ui="{ icon: { trailing: { pointer: '' } } }">
                <template #trailing>

                    <UPopover :popper="{ placement: 'top-end' }">
                        <UButton size="sm" variant="ghost" icon="i-heroicons-calendar-days-20-solid" :label="format(date, 'd MMM, yyy')" />
                        
                        <template #panel="{ close }">
                            <client-only>
                                <VDatePicker :is-dark="isDark" color="green" v-model="date" is-required @close="close"/>
                            </client-only>
                        </template>
                    </UPopover>
                </template>
                </UInput>
                <UButton size="xl" class="ml-8" color="primary" variant="soft" @click="addTask">
                    Agregar
                </UButton>
                </div>
        </UCard>
    </div>
</template>

<style>
.content-below-appbar {
    height: calc(100vh - 72px)
}
</style>