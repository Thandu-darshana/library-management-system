import React, { useState } from 'react';
import { Box, TextField, Button, MenuItem, Select, InputLabel, FormControl } from '@mui/material';
import { blue } from '@mui/material/colors';

function AddBookForm() {
  const [formValues, setFormValues] = useState({
    title: '',
    author: '',
    category: '',
    ISBN: '',
    year: '',
  });

  const categories=['a','b','c','d',''];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission (e.g., send formValues to backend or handle locally)
    console.log(formValues);
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
       
         display: 'flex',
         flexDirection: 'column',
         gap: 2,
         maxWidth: 600, // Increase maxWidth to your desired size
         mx: 'auto',
         mt: 6,
         p: 2, 
      }}
    >
      <TextField
        label="Book Title"
        name="title"
        value={formValues.title}
        onChange={handleInputChange}
        required
      />
      <TextField
        label="Author"
        name="author"
        value={formValues.author}
        onChange={handleInputChange}
        required
      />
       <FormControl fullWidth required>
        <InputLabel id="category-label">Category</InputLabel>
        <Select
          labelId="category-label"
          name="category"
          value={formValues.category}
          onChange={handleInputChange}
        >
          {categories.map((category) => (
            <MenuItem key={category} value={category}>
              {category}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        label="ISBN"
        name="ISBN"
        value={formValues.ISBN}
        onChange={handleInputChange}
        required
      />
      <TextField
        label="Added On"
        name="addon"
        value={formValues.addon}
        onChange={handleInputChange}
        required
      />
      <TextField
        label="Available"
        name="available"
        value={formValues.available}
        onChange={handleInputChange}
        required
      />
      <Button type="submit" variant="contained" color="primary">
        Add Book
      </Button>
    </Box>
  );
}

export default AddBookForm;
