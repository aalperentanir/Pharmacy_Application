import axios from "axios"
import { GET_USER_FAILURE, GET_USER_REEQUEST, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS } from "./ActionType"


export const register = (userData) => async(dispatch) =>{
    dispatch({type:REGISTER_REQUEST})

    const base_url = "http://localhost:8080/auth/v0"

    try{
        const response = await axios.post(`${base_url}/signup`,userData);
        const user = response.data
        console.log("user:",user)
        localStorage.setItem("jwt",user.data.token);
        console.log("Signup Request: ",user);;
        dispatch({type:REGISTER_SUCCESS, payload:user.data.token})
    }catch(error){

        console.log("error: ",error);
        dispatch({type:REGISTER_FAILURE, payload:error.message})

    }
}


export const login = (userData) => async(dispatch) =>{
    dispatch({type:LOGIN_REQUEST})

    const base_url = "http://localhost:8080/auth/v0"

    try{
        const response = await axios.post(`${base_url}/signin`,userData.data);
        console.log("response",response)
        const user = response.data

        console.log("Signin Request: ",user);

        dispatch({type:LOGIN_SUCCESS, payload:user.data.token})
        localStorage.setItem("jwt",user.data.token);
        userData.navigate("/")
        
    }catch(error){

        console.log("error: ",error);
        dispatch({type:LOGIN_FAILURE, payload:error.message})

    }
}

export const getUser = (jwt) => async(dispatch) =>{
    dispatch({type:GET_USER_REEQUEST})

    const base_url ="http://localhost:8080/api/users/v0"

    try {
        const response = await axios.get(`${base_url}/profile`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });
        const user = response.data.data;
        console.log("Get User Profile Request: ",user);
        dispatch({type:GET_USER_SUCCESS, payload:user})
    } catch (error) {
        console.log(error);
        dispatch({type:GET_USER_FAILURE, payload:error.message})
        
    }
}

export const logout = () => (dispatch) =>{
    localStorage.clear();
    dispatch({type:LOGOUT})
}