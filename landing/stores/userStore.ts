export const userStore = defineStore('user', {
    state: () => ({
        name: '',
        email: '',
    }),
    actions: {
        setUser(user: any) {
            this.name = user.name
            this.email = user.email
        },
        getUser() {
            return {
                name: this.name,
                email: this.email,
            }
        }
    },
    persist: true
})