import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Paper from '@mui/material/Paper';
import { getBooks } from '../services/apiService'; // Import your API service

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
    width: 100,
  },
];

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
