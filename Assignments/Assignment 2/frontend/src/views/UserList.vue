<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
      <v-btn @click="viewBooks">View Books</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedItem"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import router from "@/router";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    addUser() {
      this.dialogVisible = true;
    },
    editUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    viewBooks() {
      router.push("/books");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
    },
  },
  async created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
