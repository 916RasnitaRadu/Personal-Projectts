import { useState } from "react";
import { TreeNodeType } from "../TreeView";

export function useGetFilesData() {
  const [fileList, setFileList] = useState<TreeNodeType[]>([]);

  const setFilesListData = (receivedData: TreeNodeType[]) => {
    setFileList(receivedData);
  };
  return { fileList, setFilesListData };
}
