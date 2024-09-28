import * as React from 'react';
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
  const [pathname, setPathname] = React.useState('/books/all-books');

  const router = React.useMemo(() => {
    return {
      pathname,
      searchParams: new URLSearchParams(),
      navigate: (path) => setPathname(String(path)),
    };
  }, [pathname]);

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
              title: 'All Books',
            },
            {
              segment: 'available-books',
              title: 'Available Books',
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
      router={router}
      theme={demoTheme}
    >
      <DashboardLayout>
        <DemoPageContent pathname={router.pathname} />
      </DashboardLayout>
    </AppProvider>
  );
}

export default DashboardLayoutNavigationNested;
