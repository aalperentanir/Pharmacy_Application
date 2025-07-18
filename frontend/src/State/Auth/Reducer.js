import { REGISTER_REQUEST, LOGIN_REQUEST, GET_USER_REEQUEST, REGISTER_SUCCESS, LOGIN_SUCCESS, GET_USER_SUCCESS, REGISTER_FAILURE, LOGIN_FAILURE, GET_USER_FAILURE, LOGOUT } from "./ActionType"


const initialState = {
    user:null,
    loading:false,
    error:null,
    jwt:null
}

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case REGISTER_REQUEST:
        case LOGIN_REQUEST:
        case GET_USER_REEQUEST:
            return {...state, loading:true, error:null}

        case REGISTER_SUCCESS:
        case LOGIN_SUCCESS:
            return {...state, loading:false, error:null, jwt:action.payload.token, user:action.payload};

        case GET_USER_SUCCESS:
            return {...state, loading:false, error:null, user:action.payload}
    
        case REGISTER_FAILURE:
        case LOGIN_FAILURE:
        case GET_USER_FAILURE:
            return {...state, loading:false, error:action.payload};

        case LOGOUT:{
            return initialState;
        }
        default:
            return state;
    }
}
export default authReducer;