import { v4 as uuid } from "uuid";
import { TreeNodeType } from "./TreeView";
import { useEffect, useState } from "react";

import { FileRequest } from "../../../domain/FileRequest";
import { useGetFilesData } from "./customHooks/SetFilesData";
import axios from "axios";
import {
  useNotificationError,
  useNotificationSuccess,
} from "src/components/NotificationComponent";

interface FilesResponse {
  data: FileRequest[];
  message: string;
}

export const Data = (bucketName: string): TreeNodeType[] => {
  const token = localStorage.getItem("user-token");
  const url = "http://localhost:8080/buckets/files?bucketName=" + bucketName;
  const { fileList, setFilesListData } = useGetFilesData();
  const showSuccessNotification = useNotificationSuccess();
  const showErrorNotification = useNotificationError();

  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };

  useEffect(() => {
    fetch(url, { headers })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((apiData: FilesResponse) => {
        const transformedData = apiData.data.map(
          (fileRequest: FileRequest) => ({
            id: uuid(),
            name: fileRequest.name.toString() + fileRequest.type.toString(),
          })
        );

        setFilesListData(transformedData);
        showSuccessNotification(apiData.message);
      })
      .catch((error: any) => {
        showErrorNotification(error.response.data.message);
      });
  }, []);

  return fileList;
};
