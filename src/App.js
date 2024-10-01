import './App.css';
import DashboardLayoutNavigationNested from './components/Dashboard';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/Login';   // Import the AllBooks component

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard/*" element={<DashboardLayoutNavigationNested />} /> {/* Dashboard as a universal layout */}
      </Routes>
    </Router>
  );
}

export default App;
