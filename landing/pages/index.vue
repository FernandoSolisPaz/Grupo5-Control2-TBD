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
    if (response)
        tasks.value = response.map(task => ({ ...task, editIsOpen: false, dateObj: textDateToDate(task.date_of_expire) }));
}

const taskLength = computed(() => tasks.value.length);

const title = ref('');



function textDateToDate(date) {
    const newDate = new Date(date+'T00:00:00');
    return newDate;
}

function formatDate(date) {
    return format(date, 'd MMM, yyy');
}



// Paginación y filtrado
const currentPage = ref(1);
const tasksPerPage = ref(5);
const filter = ref('');
const filterStatus = ref('All');

const filteredTasks = computed(() => {
  return tasks.value
    .filter(task => {
      const filterText = filter.value.toLowerCase();
      const matchesTitle = task.title.toLowerCase().includes(filterText);
      const matchesDescription = task.description.toLowerCase().includes(filterText);

      if (filterStatus.value === 'All') {
        return matchesTitle || matchesDescription;
      } else {
        return (matchesTitle || matchesDescription) && task.state === filterStatus.value;
      }
    })
    .slice((currentPage.value - 1) * tasksPerPage.value, currentPage.value * tasksPerPage.value);
});


const filteredTaskCount = computed(() => {
  return tasks.value.filter(task =>
    task.title.toLowerCase().includes(filter.value.toLowerCase())
  ).length;
});

function toggleTaskState(task, value) {
    task.state = value ? 'Completed' : 'Pending';
    editOnClose(task);
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

// Añadir tarea
async function addTask() {
    if (!title.value) return;

    const newTask = {
        title: title.value,
        description: '',
        state: 'Pending',
        date_of_expire: format(date.value, 'yyyy-MM-dd')
    }
    try {
        const response = await useApi().addTask(user.getUserId(), newTask, tokenCookie.value);
        tasks.value.push({
            ...newTask,
            editIsOpen: false,
            dateObj: textDateToDate(response.date_of_expire),
            task_id: response.task_id

        });
        title.value = '';
        date.value = new Date();
        toast.add({ title:'Tarea añadida', color:'green', icon:'i-heroicons-check-circle-16-solid', });

        if ((tasks.value.length - 1) % tasksPerPage.value === 0 && currentPage.value * tasksPerPage.value < tasks.value.length) {
            currentPage.value++;
        }
    } catch (error) {
        if (error.response){
            if (error.response.status === 400) {
                const toast = useToast();
                toast.add({ title: 'Error en los datos ingresados', color: 'red' });
            } else {
                const toast = useToast();
                toast.add({ title: 'Error en el servidor', color: 'red' });
            }
        }
    }
}

// Editar tarea
async function editOnClose(task) {
    try {
        const editedTask = {
            task_id: task.task_id,
            user_id: user.getUserId(),
            title: task.title,
            description: task.description,
            state: task.state,
            date_of_expire: format(task.dateObj, 'yyyy-MM-dd')
        }
        const response = await useApi().updateTask(editedTask, tokenCookie.value);
        task.editIsOpen = false;
        toast.add({ title:'Tarea editada', color:'green', icon:'i-heroicons-check-circle-16-solid', });
    } catch (error) {
        if (error.response){
            if (error.response.status === 400) {
                toast.add({ title: 'Error en los datos ingresados', color: 'red' });
            } else {
                toast.add({ title: 'Error en el servidor', color: 'red' });
            }
        }
    }
}

// Eliminar tarea
async function deleteTask(task) {
    try {
        await useApi().deleteTask(task.task_id, tokenCookie.value);
        toast.add({ title:'Tarea eliminada', color:'red', icon:'i-heroicons-trash-16-solid', });
        // Elimina la tarea del arreglo de tareas
        tasks.value = tasks.value.filter(t => t.task_id !== task.task_id);
        if (currentPage.value > 1 && !filteredTasks.value.length) {
            currentPage.value--;
        }

    } catch (error) {
        if (error.response){
            if (error.response.status === 400) {
                toast.add({ title: 'Error en los datos ingresados', color: 'red' });
            } else {
                toast.add({ title: 'Error en el servidor', color: 'red' });
            }
        }
    }
}

onBeforeMount(async () => {
    await getTasks();
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
                <UModal v-model="task.editIsOpen" @close="editOnClose(task)">
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
                            <UCheckbox :modelValue="task.state === 'Completed'" @update:modelValue="value => toggleTaskState(task, value)" class="text-primary py-2"/>
                            <p class="text-primary font-medium px-4 hover:text-blue-500 cursor-pointer" @click="task.editIsOpen = true">{{ task.title }}</p>
                        </div>
                        <div class="flex items-center">
                            <p class="text-primary font-medium mr-2">{{ format(task.dateObj, 'd MMM, yyy') }}</p>
                            <UButton class="delete" variant="link" color="red" icon="i-heroicons-trash" @click="deleteTask(task)" />
                        </div>
                    </div>
                    <div>
                        <p v-if="task.description.length <= 20 && task.description != ''" class="mx-8 opacity-50">{{ task.description }}</p>
                        <p v-if="task.description.length > 20" class="mx-8 opacity-50">{{ task.description.slice(0, 20) + '...' }}</p>
                    </div>
                    </UCard>
            </div>

            <div v-else class="text-center">
                <p class="text-primary mx-10 py-2 font-medium">Intenta añadiendo una nueva tarea</p>
                <UIcon name="i-heroicons-arrow-down" class="w-8 h-8 text-primary mt-2"/>
            </div>
            <UPagination :total="filteredTaskCount" :pageCount="5" v-model="currentPage" class="my-4 mx-10 text-center"/>
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