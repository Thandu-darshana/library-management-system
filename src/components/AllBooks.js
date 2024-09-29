// src/components/AllBooks.js
import React, { useEffect, useState } from 'react';
import { getBooks } from '../services/apiService'; // Import your API service

const AllBooks = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const fetchedBooks = await getBooks();
        setBooks(fetchedBooks);
      } catch (error) {
        console.error('Failed to fetch books:', error);
      }
    };

    fetchBooks();
  }, []);

  return (
    <div>
      <h2>All Books</h2>
      <ul>
        {books.map(book => (
          <li key={book.book_id}>{book.title} by {book.author.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default AllBooks;
