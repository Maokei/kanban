/*import rxjs from "rxjs";
import { combineEpics, ofType } from 'redux-observable';
import { ajax } from 'rxjs/ajax';
import { map, mergeMap } from "rxjs/operators";
import axios from "axios";
import { Observable } from 'rxjs';

const FETCH_PROJECT = "FETCH_PROJECT";

//action
const fetchProject = project => ({ type: "FETCH_PROJECT", payload: project });
const fetchProjectFulfilled = payload => ({ type: "FETCH_PROJECT_FULFILLED", payload });

//epic
const fetchProjectEpic = action$ => action$.pipe(
    ofType(FETCH_PROJECT),
    mergeMap(action =>
        ajax.getJSON(`https://api.github.com/users/${action.payload}`).pipe(
            map(response => fetchProjectFulfilled(response))
        )
    )
);

export default combineEpics(
    fetchProjectEpic
);*/