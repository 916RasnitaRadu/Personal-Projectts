import { ToastProvider, useToasts } from "react-toast-notifications";

export const useNotificationSuccess = () => {
  const { addToast } = useToasts();

  return (message: string) => {
    addToast(message, {
      appearance: "success",
      autoDismissTimeout: 1500,
      autoDismiss: true,
      style: {
        position: "fixed",
        bottom: "1rem",
        right: "1rem",
      },
    });
  };
};
export const useNotificationError = () => {
  const { addToast } = useToasts();

  return (message: string) => {
    addToast(message, {
      appearance: "error",
      autoDismissTimeout: 1500,
      autoDismiss: true,
      style: {
        position: "fixed",
        bottom: "1rem",
        right: "1rem",
      },
    });
  };
};
