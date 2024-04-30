<script setup>
import { z } from 'zod';
const colorMode = useColorMode();

const user = userStore();
const tokenCookie = useCookie('token');
const router = useRouter();
if (tokenCookie.value)
    router.push('/');

const config = useRuntimeConfig();

const schema = z.object({
    email: z.string().email('Correo inválido'),
    password: z.string().min(8, 'Contraseña debe tener al menos 8 carácteres'),
    confirmPassword: z.string().min(8, 'Contraseña debe tener al menos 8 carácteres')

}).refine(data => data.password === data.confirmPassword, {
    message: 'Las contraseñas no coinciden',
    path: ['confirmPassword'],
});

const state = reactive({
    name: undefined,
    lastName: undefined,
    email: undefined,
    password: undefined,
    confirmPassword: undefined,
});

async function handleSubmit (event) {

    const result = schema.safeParse(state);
    if (!result.success) {
        const toast = useToast();
        toast.add({ title: 'Error en los datos ingresados', color: 'red' });
        return;
    }
    const userObj = {
                name: state.name + ' ' + state.lastName,
                email: state.email,
                password: state.password,
            };


    const body = JSON.stringify(userObj);
    try {
        const response = await $fetch('http://localhost:8080/api/user/register', {
            method: 'POST',
            body: body,
        });
        tokenCookie.value = response;
        const toast = useToast();
        toast.add({ title: 'Usuario registrado', color: 'green' });
        // Esperar dos segundos antes de redirigir
        await new Promise(resolve => setTimeout(resolve, 2000));
        const router = useRouter();
        router.push('/');

    } catch (error) {
        if (error.response.status === 400) {
            const toast = useToast();
            toast.add({ title: 'Error en los datos ingresados', color: 'red' });
        } else if (error.response.status === 409) {
            const toast = useToast();
            toast.add({ title: 'El correo ya está registrado', color: 'red' });
        } else {
            const toast = useToast();
            toast.add({ title: 'Error en el servidor', color: 'red' });
        }
    }
}
</script>

<template>
    <div class="flex justify-center items-center h-screen">
        <UCard class="w-3/5 max-w-2xl">
            <div>
                <h1 class="text-primary font-bold text-center text-inherit text-3xl pb-5 pt-2">Registro de Usuario</h1>
            </div>
            <UForm :schema="schema" :state="state" class="space-y-4 text-center" @submit="handleSubmit">
                <UFormGroup label="Nombres" name="name">
                    <UInput v-model="state.name" />
                </UFormGroup>

                <UFormGroup label="Apellidos" name="lastName">
                    <UInput v-model="state.lastName" />
                </UFormGroup>

                <UFormGroup label="Email" name="email">
                    <UInput placeholder="you@example.com" v-model="state.email" />
                </UFormGroup>
                
                <UFormGroup label="Contraseña" name="password">
                    <UInput v-model="state.password" type="password" />
                </UFormGroup>
                
                <UFormGroup label="Confirmar Constraseña" name="confirmPassword">
                    <UInput v-model="state.confirmPassword" type="password" />
                </UFormGroup>

                <UFormGroup>
                    <UButton class="w-1/3 max-w-52 justify-center" type="submit">
                        Submit
                    </UButton>
                </UFormGroup>
                <UFormGroup>
                    <ULink
                    to="/login"
                    class="text-primary text-end underline text-sm"
                    >
                        Ya tengo una cuenta
                    </ULink>
                </UFormGroup>
            </UForm>
        </UCard>
    </div>
</template>