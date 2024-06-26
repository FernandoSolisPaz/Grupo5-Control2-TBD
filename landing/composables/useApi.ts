import { userStore } from '~/stores/userStore'

export default function useApi($pinia : any) {
    
    const url = 'http://localhost:8080';
    const user = userStore($pinia);
    const toast = useToast();
    const router = useRouter();
        
    // User API
    
    // Create
    async function login(name : String, password : String) {
        try {
            const response : any = await $fetch(`${url}/api/user/login`, {
                method: 'POST',
                body: JSON.stringify({ name: name, password: password }),
            });
            const tokenCookie = useCookie('token');
            tokenCookie.value = response;
            const userObj = await useApi($pinia).getUser(name);
            user.setUser(userObj);
        } catch (error : any) {
            return error;
        }
    }
    // Read
    async function getUser(name : String) {
        try {
            const response = await $fetch(`${url}/api/user/name/${name}`, {
                method: 'GET'
            });
        return response;
        } catch (error : any) {
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
    
    // Task API
    // Create
    async function addTask(user_id : Number, task : any, token : String) {
        try {
            const response = await $fetch(`${url}/api/task`, { 
                method: 'POST',
                body: JSON.stringify({...task, user_id: user_id}),
                params: { token : token }
            });
            return response;
        } catch (error : any) {
            return error;
        }
    }   
    // Read
    async function getTasksById(user_id : Number, token : String) {
        try {
            const response = await $fetch(`${url}/api/tasks/user/${user_id}`, {
                method: 'GET',
                params: { token : token }
            });
            return response;
        } catch (error : any) {
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
    // Update
    async function updateTask(task : any, token : String) {
        try {
            const response = await $fetch(`${url}/api/task/${task.task_id}`, {
                method: 'PUT',
                body: JSON.stringify(task),
                params: { token : token }
            });
            return response;
        } catch (error : any) {
            return error;
        }
    }
    // Delete
    async function deleteTask(task_id : Number, token : String) {
        try {
            const response = await $fetch(`${url}/api/task/${task_id}`, {
                method: 'DELETE',
                params: { token : token }
            });
            return response;
        } catch (error : any) {
            return error;
        }
    }
    return {
        login,
        getUser,
        getTasksById,
        addTask,
        updateTask,
        deleteTask
    }
    
}