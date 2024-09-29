import React, { useState } from 'react';
import { Box, TextField, Button, MenuItem, Select, InputLabel, FormControl } from '@mui/material';
import { blue } from '@mui/material/colors';

function AddAuthorForm() {
  const [formValues, setFormValues] = useState({
    firstname: '',
    lastname: '',
    
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
        label="First Name"
        name="firstname"
        value={formValues.title}
        onChange={handleInputChange}
        required
      />
      <TextField
        label="Last Name"
        name="lastname"
        value={formValues.author}
        onChange={handleInputChange}
        required
      />
      
      <Button type="submit" variant="contained" color="primary">
        Add Author
      </Button>
    </Box>
  );
}

export default AddAuthorForm;

