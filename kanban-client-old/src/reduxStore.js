import { createStore, applyMiddleware, compose} from "redux";
import { createEpicMiddleware } from 'redux-observable';
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};
const epicMiddleware = createEpicMiddleware();
const middleware = [thunk, epicMiddleware];

let store;
const ReactReduxDevTools =
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
    store = createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleware(...middleware),
            ReactReduxDevTools
        )
    );
} else {
    store = createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleware(...middleware)
        )
    );
}

export default store;