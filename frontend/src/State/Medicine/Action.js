import api, { ADMIN_URL, BASE_URL } from "@/Config/api";
import {
  GET_ALL_MEDICINES_FAILURE,
  GET_ALL_MEDICINES_REQUEST,
  GET_ALL_MEDICINES_SUCCESS,
  GET_MEDICINE_BY_ID_FAILURE,
  GET_MEDICINE_BY_ID_REQUEST,
  GET_MEDICINE_BY_ID_SUCCESS,
  GET_MEDICINES_BY_SKIN_TYPE_FAILURE,
  GET_MEDICINES_BY_SKIN_TYPE_REQUEST,
  GET_MEDICINES_BY_SKIN_TYPE_SUCCESS,
  SEARCH_MEDICINES_FAILURE,
  SEARCH_MEDICINES_REQUEST,
  SEARCH_MEDICINES_SUCCESS,
  CREATE_MEDICINE_FAILURE,
  CREATE_MEDICINE_REQUEST,
  CREATE_MEDICINE_SUCCESS,
  UPDATE_MEDICINE_FAILURE,
  UPDATE_MEDICINE_REQUEST,
  UPDATE_MEDICINE_SUCCESS,
  DELETE_MEDICINE_FAILURE,
  DELETE_MEDICINE_REQUEST,
  DELETE_MEDICINE_SUCCESS,
} from "./ActionType";

export const getAllMedicines = (jwt) => async(dispatch) => {
    dispatch({type:GET_ALL_MEDICINES_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/medicines`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: GET_ALL_MEDICINES_SUCCESS, payload: response.data });
        console.log("getAllMedicines",response.data);
    } catch (error) {
        dispatch({ type: GET_ALL_MEDICINES_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const getMedicineById = ({id,jwt}) => async(dispatch) => {
    dispatch({type:GET_MEDICINE_BY_ID_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/medicines/${id}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: GET_MEDICINE_BY_ID_SUCCESS, payload: response.data });
        console.log("getMedicineById",response.data);
    } catch (error) {
        dispatch({ type: GET_MEDICINE_BY_ID_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const getMedicineBySkinType = ({skinType,jwt}) => async(dispatch) => {
    dispatch({type:GET_MEDICINES_BY_SKIN_TYPE_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/medicines/skin-type/${skinType}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: GET_MEDICINES_BY_SKIN_TYPE_SUCCESS, payload: response.data });
        console.log("getMedicineBySkinType",response.data);
    } catch (error) {
        dispatch({ type: GET_MEDICINES_BY_SKIN_TYPE_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const searchMedicine = ({keyword,jwt}) => async(dispatch) => {
    dispatch({type:SEARCH_MEDICINES_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/medicines/search/`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            },
            params:{
                keyword
            }
        })
        dispatch({ type: SEARCH_MEDICINES_SUCCESS, payload: response.data });
        console.log("searchMedicine",response.data);
    } catch (error) {
        dispatch({ type: SEARCH_MEDICINES_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const createMedicine = ({medicineData,jwt}) => async(dispatch) => {
    dispatch({type:CREATE_MEDICINE_REQUEST})

    try {
        const response = await api.post(`${ADMIN_URL}/admin/v0/medicines`,medicineData,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: CREATE_MEDICINE_SUCCESS, payload: response.data });
        console.log("createMedicine",response.data);
    } catch (error) {
        dispatch({ type: CREATE_MEDICINE_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const updateMedicine = ({medicineData,jwt,id}) => async(dispatch) => {
    dispatch({type:UPDATE_MEDICINE_REQUEST})

    try {
        const response = await api.put(`${ADMIN_URL}/admin/v0/medicines/${id}`,medicineData,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: UPDATE_MEDICINE_SUCCESS, payload: response.data });
        console.log("updateMedicine",response.data);
    } catch (error) {
        dispatch({ type: UPDATE_MEDICINE_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}

export const deleteeMedicine = ({id,jwt}) => async(dispatch) => {
    dispatch({type:DELETE_MEDICINE_REQUEST})

    try {
        const response = await api.delete(`${ADMIN_URL}/admin/v0/medicines/${id}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })
        dispatch({ type: DELETE_MEDICINE_SUCCESS, payload: id });
        console.log("deleteeMedicine",response.data);
    } catch (error) {
        dispatch({ type: DELETE_MEDICINE_FAILURE, error: error.message });
        console.log("error",error)
        
    }
}