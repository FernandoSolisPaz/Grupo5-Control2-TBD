export const userStore = defineStore('user', {
    state: () => ({
        user_id: -1,
        name: '',
        email: '',
    }),
    actions: {
        setUser(user: any) {
            this.user_id = user.user_id
            this.name = user.name
            this.email = user.email
        },
        getUser() {
            return {
                user_id: this.user_id,
                name: this.name,
                email: this.email,
            }
        },
        getUserId() {
            return this.user_id
        }
    },
    persist: true
})