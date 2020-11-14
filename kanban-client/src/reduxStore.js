import { createStore, applyMiddleware, compose} from "redux";
import { createEpicMiddleware } from 'redux-observable';
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};
const epicMiddleware = createEpicMiddleware();
const middleware = [thunk, epicMiddleware];

let store;
if(window.navigator.userAgent.includes("Chrome")) {
    store = createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleware(...middleware),
            window.__REDUX_DEVTOOLS_EXTENSION__ &&
            window.__REDUX_DEVTOOLS_EXTENSION__()
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