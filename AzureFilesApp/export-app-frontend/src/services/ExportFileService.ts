import axios from "axios";
import { useNodeNames } from "../components/molecules/FileBrowser/customHooks/NodeNames";
import { useEffect } from "react";
import {
  useNotificationError,
  useNotificationSuccess,
} from "src/components/NotificationComponent";
import { error } from "console";

const exportUrl = "http://localhost:8080/files/export";
const token = localStorage.getItem("user-token");

const headers = {
  Authorization: `Bearer ${token}`,
  "Content-Type": "application/json",
};

interface ExportFilesToCSVProps {
  show: boolean;
}

export function ExportFilesToCSV({ show }: ExportFilesToCSVProps) {
  const { nodeName, setNodes } = useNodeNames();
  const showSuccessNotification = useNotificationSuccess();
  const showErrorNotification = useNotificationError();

  useEffect(() => {
    if (show) {
      fetchData();
    }
  }, [show]);

  const fetchData = () => {
    axios
      .post(exportUrl, nodeName, { headers })
      .then((response) => {
        showSuccessNotification(response.data.message);
        resetList();
      })
      .catch((error) => {
        showErrorNotification(getErrorMessage(error));
      });
  };

  const resetList = () => {
    const emptyString: String[] = [];
    setNodes(emptyString);
  };

  const getErrorMessage = (error: any) => {
    if (error.response) {
      return error.response.data.message;
    }
    return "An error occurred while making the request.";
  };

  return null;
}
