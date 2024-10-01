import * as React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { createTheme } from '@mui/material/styles';
import AddBoxTwoToneIcon from '@mui/icons-material/AddBoxTwoTone';
import { AppProvider } from '@toolpad/core/AppProvider';
import { DashboardLayout } from '@toolpad/core/DashboardLayout';
import AutoStoriesTwoToneIcon from '@mui/icons-material/AutoStoriesTwoTone';
import BarChartIcon from '@mui/icons-material/BarChart';
import DemoPageContent from './DemoPageContent';  // Import the DemoPageContent component


import AllBooks from './AllBooks';

const demoTheme = createTheme({
  cssVariables: {
    colorSchemeSelector: 'data-toolpad-color-scheme',
  },
  colorSchemes: { light: true, dark: true },
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 600,
      lg: 1200,
      xl: 1536,
    },
  },
});

function DashboardLayoutNavigationNested() {
  return (
    <AppProvider
      branding={{
        logo: <AutoStoriesTwoToneIcon fontSize="large" color="primary" sx={{ fontSize: 35 }} />,
        title: 'Library Management System',
      }}
      navigation={[
        {
          segment: 'books',
          title: 'Books',
          children: [
            {
              segment: 'all-books',
              title: (
                <Link to="/dashboard/books/allBooks">View All Books</Link> // Link to internal route
              ),
            },
            {
              segment: 'available-books',
              title: (
                <Link to="/dashboard/books/availableBooks">View Available Books</Link> // Link for available books
              ),
            },
            {
              segment: 'borrowed-books',
              title: 'Borrowed Books',
            },
            {
              segment: 'add-books',
              title: 'Add Books',
              icon: <AddBoxTwoToneIcon />,
            },
          ],
        },
        {
          segment: 'authors',
          title: 'Authors',
          children: [
            {
              segment: 'all-authors',
              title: 'All Authors',
            },
            {
              segment: 'add-authors',
              title: 'Add Author',
              icon: <AddBoxTwoToneIcon />,
            },
          ],
        },
        {
          segment: 'category',
          title: 'Category',
          children: [
            {
              segment: 'choose-category',
              title: 'Choose Category',
            },
          ],
        },
        {
          segment: 'fine',
          title: 'Fine',
          children: [
            {
              segment: 'view-fines',
              title: 'View Fines',
            },
            {
              segment: 'paid-fines',
              title: 'Paid Fines',
            },
          ],
        },
        {
          segment: 'checkout',
          title: 'Check Out',
          children: [
            {
              segment: 'view-checkout',
              title: 'View Checkout',
            },
            {
              segment: 'add-checkout',
              title: 'Add Checkout',
              icon: <AddBoxTwoToneIcon />,
            },
          ],
        },
        {
          kind: 'divider',
        },
        {
          kind: 'header',
          title: 'Analytics',
        },
        {
          segment: 'reports',
          title: 'Reports',
          icon: <BarChartIcon />,
        },
      ]}
      theme={demoTheme}
    >
      <DashboardLayout>
        {/* Use Routes to switch between different dashboard content */}
        <Routes>
          <Route path="/books/allBooks" element={<AllBooks />} />
          {/* Add more routes here as needed */}
          <Route path="/" element={<DemoPageContent pathname="/dashboard" />} />
        </Routes>
      </DashboardLayout>
    </AppProvider>
  );
}

export default DashboardLayoutNavigationNested;
