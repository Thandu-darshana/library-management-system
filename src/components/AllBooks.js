import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import { getBooks, deleteBook } from '../services/apiService'; // Import your API service

const handleEdit = (row) => {
  // Here you can add the logic to edit the book.
  // For example, open a modal to edit the book details.
  console.log('Edit book:', row);
};

export default function AllBooks() {
  const [books, setBooks] = React.useState([]);
  const [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    const fetchBooks = async () => {
      try {
        const fetchedBooks = await getBooks();
        setBooks(fetchedBooks);
      } catch (error) {
        console.error('Failed to fetch books:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchBooks();
  }, []);

  const handleDelete = async (bookId) => {
    try {
      await deleteBook(bookId); // Delete the book by ID via the API service
      setBooks((prevBooks) => prevBooks.filter((book) => book.book_id !== bookId)); // Remove the deleted book from state
    } catch (error) {
      console.error('Failed to delete book:', error);
    }
  };

  const columns = [
    { field: 'book_id', headerName: 'ID', width: 70 },
    { field: 'title', headerName: 'Title', width: 200 },
    {
      field: 'author',
      headerName: 'Author',
      width: 150,
      valueGetter: (params) => params.row?.author?.name || 'Unknown', // Safely access author name
    },
    { field: 'isbn', headerName: 'ISBN', width: 200 },
    { field: 'copies', headerName: 'Copies', width: 100 },
    {
      field: 'category',
      headerName: 'Category',
      width: 150,
      valueGetter: (params) => params.row?.category?.name || 'Unknown', // Safely access category name
    },
    {
      field: 'available',
      headerName: 'Available',
      type: 'boolean',
      width: 100,
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 200,
      renderCell: (params) => (
        <>
          <Button
            variant="contained"
            color="primary"
            size="small"
            onClick={() => handleEdit(params.row)}
            style={{ marginRight: 10 }}
          >
            Edit
          </Button>
          <Button
            variant="contained"
            color="secondary"
            size="small"
            onClick={() => handleDelete(params.row.book_id)}
          >
            Delete
          </Button>
        </>
      ),
    },
  ];

  return (
    <Paper sx={{ height: 400, width: '100%' }}>
      <DataGrid
        rows={books}
        columns={columns}
        pageSize={5}
        loading={loading}
        getRowId={(row) => row.book_id} // Use book_id as the unique row identifier
        checkboxSelection
        sx={{ border: 0 }}
      />
    </Paper>
  );
}
