<template>
  <v-card>
    <v-card-title>
      Bookshop
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="searchBook">Search</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      @click:row="sellItem"
    ></v-data-table>
    <SellDialog
      :opened="dialogVisible"
      :book="selectedItem"
      @refresh="refreshList"
    ></SellDialog>
  </v-card>
</template>

<script>
import api from "../api";
import SellDialog from "../components/SellDialog";

export default {
  name: "BookListEmployee",
  components: { SellDialog },
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
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    sellItem(book) {
      this.selectedItem = book;
      this.dialogVisible = true;
    },
    async searchBook() {
      if(this.search === ""){
        this.books = await api.books.allBooks();
      } else {
        this.books = await api.books.filteredBooks(this.search);
      }
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
