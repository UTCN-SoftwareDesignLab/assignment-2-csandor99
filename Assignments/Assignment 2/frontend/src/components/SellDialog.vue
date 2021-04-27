<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Sell book </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" disabled />
          <v-text-field v-model="book.author" label="Author" disabled />
          <v-text-field v-model="book.genre" label="Genre" disabled />
          <v-text-field v-model="book.quantity" label="Quantity" disabled />
          <v-text-field v-model="book.price" label="Price" disabled />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Sell </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SellDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.books
        .sell({
          id: this.book.id,
          title: this.book.title,
          author: this.book.author,
          genre: this.book.genre,
          quantity: this.book.quantity,
          price: this.book.price,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
