import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("http://localhost:8080/api/project", project);
    console.log(res);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjects = () => async dispatch => {
  const res = await axios.get("/api/project/all");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

export const getProject = (pi, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${pi}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch(error) {
    history.push("/dashboard");
  }
};

export const deleteProject = (pi) => async dispatch => {
  try {
    await axios.delete(`/api/project/${pi}`)
    dispatch({
      type: DELETE_PROJECT,
      payload: pi
    });
  }catch(error) {
    console.log("error delete")
  }
}