import { useState } from "react";

export const useSetCheckboxState = () => {
  const [isCheckedState, setIsCheckedState] = useState(false);

  const setCheckboxState = (state: boolean) => {
    setIsCheckedState(state);
  };

  return { isCheckedState, setCheckboxState };
};
