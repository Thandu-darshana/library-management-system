import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Box, Typography, List, ListItem, ListItemText } from '@mui/material';
import AddBookForm from './AddBookForm';  // Import the AddBookForm component
import AddAuthorForm from './AddAuthorForm';
import { getBooks} from '../services/apiService';

function DemoPageContent({ pathname }) {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    const loadBooks = async () => {
      try {
        const response = await getBooks(); // Assuming fetchBooks fetches the list of books
        setBooks(response.data); // Adjust based on your API response structure
      } catch (error) {
        console.error("Error fetching books:", error);
      }
    };

    if (pathname === '/books/allBooks') {
      loadBooks();
    }
  }, [pathname]);

  return (
    <Box
      sx={{
        py: 4,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        textAlign: 'center',
      }}
    >
      {pathname === '/books/addBook' ? (
        <AddBookForm />
      ) : pathname === '/authors/add-authors' ? (
        <AddAuthorForm />
      ) : pathname === '/books/allBooks' ? (
        <>
          <Typography variant="h4">All Books</Typography>
          {Array.isArray(books) && books.length > 0 ? (
            <List>
              {books.map(book => (
                <ListItem key={book.book_id}>
                  <ListItemText 
                    primary={book.title} 
                    secondary={`Author: ${book.author.name || 'Unknown'}, ISBN: ${book.isbn}, Copies: ${book.copies}`} 
                  />
                </ListItem>
              ))}
            </List>
          ) : (
            <Typography>No books available.</Typography>
          )}
        </>
      ) : (
        <Typography>Dashboard content for {pathname}</Typography>
      )}
    </Box>
  );
}

DemoPageContent.propTypes = {
  pathname: PropTypes.string.isRequired,
};

export default DemoPageContent;