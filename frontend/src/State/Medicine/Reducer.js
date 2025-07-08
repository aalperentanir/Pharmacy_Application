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

const initialState = {
  medicines: [],
  filteredMedicines: [],
  currentMedicine: null,
  loading: false,
  error: null,
};

export const medicineReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_ALL_MEDICINES_REQUEST:
    case GET_MEDICINE_BY_ID_REQUEST:
    case GET_MEDICINES_BY_SKIN_TYPE_REQUEST:
    case SEARCH_MEDICINES_REQUEST:
    case CREATE_MEDICINE_REQUEST:
    case UPDATE_MEDICINE_REQUEST:
    case DELETE_MEDICINE_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    case GET_ALL_MEDICINES_SUCCESS:
      return {
        ...state,
        loading: false,
        medicines: action.payload.data,
      };

    case GET_MEDICINE_BY_ID_SUCCESS:
      return {
        ...state,
        loading: false,
        currentMedicine: action.payload,
      };

    case GET_MEDICINES_BY_SKIN_TYPE_SUCCESS:
    case SEARCH_MEDICINES_SUCCESS:
      return {
        ...state,
        loading: false,
        filteredMedicines: action.payload,
      };

    case CREATE_MEDICINE_SUCCESS:
      return {
        ...state,
        loading: false,
        medicines: [...state.medicines, action.payload],
      };

    case UPDATE_MEDICINE_SUCCESS:
      return {
        ...state,
        loading: false,
        medicines: state.medicines.map((medicine) =>
          medicine.id === action.payload.id ? action.payload : medicine
        ),
        currentMedicine: action.payload,
      };

    case DELETE_MEDICINE_SUCCESS:
      return {
        ...state,
        loading: false,
        medicines: state.medicines.filter((medicine) => medicine.id !== action.payload),
      };

    case GET_ALL_MEDICINES_FAILURE:
    case GET_MEDICINE_BY_ID_FAILURE:
    case GET_MEDICINES_BY_SKIN_TYPE_FAILURE:
    case SEARCH_MEDICINES_FAILURE:
    case CREATE_MEDICINE_FAILURE:
    case UPDATE_MEDICINE_FAILURE:
    case DELETE_MEDICINE_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.error,
      };

    default:
      return state;
  }
};