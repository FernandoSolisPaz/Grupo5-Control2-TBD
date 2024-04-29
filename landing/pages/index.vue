<script setup>

import { format } from 'date-fns'

const colorMode = useColorMode();

const isDark = computed({
  get () {
    return colorMode.value === 'dark'
  },
  set () {
    colorMode.preference = colorMode.value === 'dark' ? 'light' : 'dark'
  }
});

const isOpen = ref(false);

const date = ref(new Date());

const user = userStore();
const router = useRouter();
const tokenCookie = useCookie('token');
if (!tokenCookie.value) {
    router.push('/login');
}
//const tasks = ref([]);
const tasks = ref([
    {
        task_id: 1,
        title: 'Tarea 1',
        description: 'Descripción de la tarea 1',
        state: 'Pending',
        date_of_expire: '2024-10-10',
        isOpen: false,
        dateObj: new Date('2024-10-10T00:00:00')
    },
    {
        task_id: 2,
        title: 'Tarea 2',
        description: 'Descripción de la tarea 2',
        state: 'Pending',
        date_of_expire: '2024-10-11',
        isOpen: false,
        dateObj: new Date('2024-10-11T00:00:00')
    }
]);

const taskLength = computed(() => tasks.value.length);

const title = ref('');

function addTask() {
    if (!title.value) return;
    const newTask = {
        task_id: tasks.value.length + 1,
        title: title.value,
        description: '',
        state: 'Pending',
        date_of_expire: format(date.value, 'dd-MM-yyyy'),
        dateObj: textDateToDate(format(date.value, 'dd-MM-yyyy')),
    }
    console.log(newTask);
    tasks.value.push(newTask);
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

</script>

<template>
    <div class="flex justify-center items-center content-below-appbar">
        <UCard class="w-screen mx-52 min-w-fit ring-0">
            <h1 class="font-bold text-primary text-3xl mx-10 py-4 mb-4">Tareas</h1>

            <div v-if="taskLength > 0" v-for="task in tasks" :key="task.task_id" class="flex px-10 items-center">
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
                            <UCheckbox v-model="task.state" class="text-primary py-2"/>
                            <p class="text-primary font-medium px-4 hover:text-blue-500 cursor-pointer" @click="task.isOpen = true">{{ task.title }}</p>
                        </div>
                        <UButton class="delete" variant="link" color="red" icon="i-heroicons-trash" @click="deleteTask(task)" />
                    </div>
                    </UCard>
            </div>

            <div v-else class="text-center">
                <p class="text-primary mx-10 py-2 font-medium">Intenta añadiendo una nueva tarea</p>
                <UIcon name="i-heroicons-arrow-down" class="w-8 h-8 text-primary mt-2"/>
            </div>
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