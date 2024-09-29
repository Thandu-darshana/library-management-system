import React from 'react';
import PropTypes from 'prop-types';
import { Box, Typography } from '@mui/material';
import AddBookForm from './AddBookForm';  // Import the AddBookForm component
import AddAuthorForm from './AddAuthorForm';

function DemoPageContent({ pathname }) {
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
      {pathname === '/books/add-books' ? (
        <AddBookForm />
      ) : (
        <Typography>Dashboard content for {pathname}</Typography>
      )}
      {pathname === '/authors/add-authors' ? (
        <AddAuthorForm />
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
