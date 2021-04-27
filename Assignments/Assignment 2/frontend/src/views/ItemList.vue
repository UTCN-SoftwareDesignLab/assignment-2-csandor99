<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Book</v-btn>
      <v-btn @click="viewUsers">View Users</v-btn>
      <v-btn @click="reportPDF">PDF Report</v-btn>
      <v-btn @click="reportCSV">CSV Report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :book="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import router from "@/router";

export default {
  name: "ItemList",
  components: { ItemDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" }
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(book) {
      this.selectedItem = book;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    viewUsers(){
      router.push("/users")
    },
    reportPDF(){
      api.books.generateReport("PDF")
    },
    reportCSV(){
      api.books.generateReport("CSV")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
