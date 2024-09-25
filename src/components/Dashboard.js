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
      <Typography>Dashboard content for {pathname}</Typography>
    </Box>
  );
}

DemoPageContent.propTypes = {
  pathname: PropTypes.string.isRequired,
};

function DashboardLayoutNavigationNested(props) {
  

  const [pathname, setPathname] = React.useState('/movies/lord-of-the-rings');

  const router = React.useMemo(() => {
    return {
      pathname,
      searchParams: new URLSearchParams(),
      navigate: (path) => setPathname(String(path)),
    };
  }, [pathname]);

  

  return (
    
    // preview-start
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
              title: 'Borowwed Books',
            },
            {
              segment: 'add-books',
              title: 'Add Books',
              icon: <AddBoxTwoToneIcon />,
            },
          ],
          
        },
        {
          segment: 'members',
          title: 'Members',
          children: [
            {
              segment: 'all-members',
              title: 'All Members',
            },
            {
              segment: 'add-members',
              title: 'Add Members',
              icon: <AddBoxTwoToneIcon />,
            },
          ]
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
          }
        
      ]}
      router={router}
      theme={demoTheme}
      
    >
      
      
      <DashboardLayout>
        <DemoPageContent pathname={pathname} />
      </DashboardLayout>
    </AppProvider>
    // preview-end
  );
}


export default DashboardLayoutNavigationNested;
