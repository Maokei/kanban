import React from 'react';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/"
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import { Provider } from "react-redux";
import store from "./reduxStore";

function App() {
  return (
      <Provider store={store}>
          <Router>
            <div className="App">
              <Header />
              {
              //Public Routes
              }

              <Route exact path="/" component={Landing} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/login" component={Login} />

              {
                //Private Routes
              }
              <Route exact path="/dashboard" component={Dashboard} />
              <Route exact path="/addProject" component={AddProject}/>
              <Route exact path="/updateProject/:id" component={UpdateProject}/>
              <Route exact path="/projectBoard/:id" component={ProjectBoard} />
              <Route
                exact
                path="/addProjectTask/:id"
                component={AddProjectTask}
            />
            <Route
              exact
              path="/updateProjectTask/:backlog_id/:pt_id"
              component={UpdateProjectTask}
            />
            </div>
          </Router>
      </Provider>
  );
}

export default App;
