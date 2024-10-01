// src/apiService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/books'; // Adjust the base URL for your endpoints

export const getBooks = async () => {
  try {
    const response = await axios.get(`${API_URL}/getAllBooks`);
    return response.data;
  } catch (error) {
    console.error('Error fetching books:', error);
    throw error;
  }
};

export const addBook = async (book) => {
  try {
    const response = await axios.post(`${API_URL}/addBook`, book);
    return response.data;
  } catch (error) {
    console.error('Error adding book:', error);
    throw error;
  }
};

export const updateBook = async (bookId, book) => {
  try {
    const response = await axios.post(`${API_URL}/updateBookById/${bookId}`, book);
    return response.data;
  } catch (error) {
    console.error('Error updating book:', error);
    throw error;
  }
};

export const deleteBook = async (bookId) => {
  try {
    await axios.delete(`${API_URL}/deleteBookById/${bookId}`);
  } catch (error) {
    console.error('Error deleting book:', error);
    throw error;
  }
};

export const getBookById = async (bookId) => {
  try {
    const response = await axios.get(`${API_URL}/getBookById/${bookId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching book by ID:', error);
    throw error;
  }
};
