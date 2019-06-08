import actions from "./actions";
import userService from "services/user";

export const createUser = newUser => async dispatch => {
  
  dispatch({ type: actions.CREATE });

  try {
    const response = await userService.create(newUser);
    if(response.ok) {
      dispatch({ type: actions.CREATE_OK });
    } else {
      dispatch({ type: actions.CREATE_FAIL });
    }
  } catch(error) {
    dispatch({ type: actions.CREATE_FAIL });
  }

}