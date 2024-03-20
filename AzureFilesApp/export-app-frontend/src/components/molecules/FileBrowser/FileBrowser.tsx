import { useEffect, useState } from "react";
import { TreeView } from "./TreeView";
import { Data } from "./Data";
import Navbar from "../../organisms/Header/Navbar";
import ExportIcon from "../../atoms/Icons/Export";
import DownloadIcon from "../../atoms/Icons/Download";
import { ExportFilesToCSV } from "../../../services/ExportFileService";
import ProgressBar from "../../atoms/ProgressBar/ProgressBar";
import "./FileBrowser.css";
import axios from "axios";
import React from "react";
import {
  useNotificationError,
  useNotificationSuccess,
} from "src/components/NotificationComponent";
interface FileBrowserProp {
  bucketName: string;
}

const FileBrowser = ({ bucketName }: FileBrowserProp) => {
  const [selected, select] = useState<string | null>(null);
  const [showExport, setShowExport] = useState(false);
  const [isRunning, setIsRunning] = useState(false);
  const bucketList = Data(bucketName);
  const initialCheckboxState: Record<string, boolean> = {};
  const showSuccessNotification = useNotificationSuccess();
  const showErrorNotification = useNotificationError();

  bucketList.forEach((bucket) => {
    initialCheckboxState[bucket.id] = false;
  });

  const [CheckboxState, setCheckboxState] = useState<Record<string, boolean>>(
    {}
  );

  const handleCheckBoxState = (
    e: React.ChangeEvent<HTMLInputElement>,
    id: string
  ) => {
    setCheckboxState((prevCheckboxState) => {
      const newState = { ...prevCheckboxState, [id]: e.target.checked };
      return newState;
    });
  };

  const handleProgressComplete = () => {
    setIsRunning(false);
  };

  const resetCheckbox = () => {
    setCheckboxState(initialCheckboxState);
  };

  const toggleExport = async () => {
    if (Object.values(CheckboxState).some((selected) => selected)) {
      setIsRunning(true);
    }
    setShowExport(!showExport);
    resetCheckbox();
  };

  const DownloadCSV = (bucketName: string) => {
    const token = localStorage.getItem("user-token");
    const timestamp = new Date()
      .toLocaleString()
      .replace(/[-:.]/g, "")
      .replace(/[/:]/g, "_")
      .replace(", ", "_");
    const fileName = `${bucketName}_${timestamp}.csv`;
    const url = `http://localhost:8080/files/download/${bucketName}`;

    const headers = {
      responseType: "blob",
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    };

    axios
      .get(url, { headers })
      .then((response) => {
        const blob = new Blob([response.data], {
          type: "application/csv",
        });
        const url = window.URL.createObjectURL(blob);

        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();

        window.URL.revokeObjectURL(url);
        showSuccessNotification("Download files completed successfully!");
      })
      .catch((error) => {
        showErrorNotification(error);
      });
  };

  const handleDownload = async () => {
    DownloadCSV(bucketName);
  };

  useEffect(() => {
    if (showExport) {
      setShowExport(!showExport);
    }
  }, [showExport]);

  return (
    <>
      {<ExportFilesToCSV show={showExport} />}
      <div>
        <Navbar />

        <div className="container h-full ml-[-10px] 2xl:my-14">
          <div className="flex h-full flex-wrap items-center justify-center text-[#343433] ">
            <div className="w-[60%] max-sm:w-96 max-md:w-9/12">
              <div className="block">
                <div className="g-0 lg:flex">
                  {/* left */}
                  <div className="block items-center justify-center text-white bg-[#343433] shadow-xl mt-6 p-7 ml-5 lg:w-[52%] lg:py-10 md:p-3 md:mt-3 lg:mt-2 xl:mt-1 2xl:mt-2">
                    <div className="flex-grow p-7 font-medium tracking-wide text-center text-3xl mb-12 md:mb-8 md:p-9 lg:p-5 lg:mb-16">
                      Bucket {bucketName}
                    </div>
                    <div className="text-[#343433] flex justify-center text-xl font-normal">
                      <button
                        className="flex mx-2 bg-white rounded-lg w-44 ml-2 my-auto"
                        data-te-toggle="tooltip"
                        title="Export files."
                        onClick={toggleExport}
                      >
                        <ExportIcon />
                        <div className="my-auto pl-1 pr-2">Export</div>
                      </button>

                      <button
                        className="flex mx-2 bg-white rounded-lg w-44 ml-2 my-auto"
                        data-te-toggle="tooltip"
                        title="Download files."
                        onClick={handleDownload}
                      >
                        <DownloadIcon />
                        <div className="my-auto pr-2">Download</div>
                      </button>
                    </div>
                    <div className="">
                      <div className="flex justify-center mt-2">
                        <ProgressBar
                          isRunning={isRunning}
                          onComplete={handleProgressComplete}
                        />
                      </div>
                    </div>
                  </div>

                  {/* right */}
                  <div className="bg-white drop-shadow shadow-lg block items-center justify-center py-10 mb-4 ml-5 lg:ml-0 lg:mb-0 lg:w-[55%] lg:mt-[8px] xl:mt-[4px]">
                    <div className="text-center ">
                      <div className="block items-center justify-center">
                        <div className="flex-grow font-medium tracking-wide text-center text-3xl py-6 md:py-4 md:mb-8 ">
                          Bucket Content
                        </div>
                        <div className="">
                          <TreeView.Root value={selected} onChange={select}>
                            {bucketList.map((node) => (
                              <TreeView.Node
                                node={node}
                                key={node.id}
                                initialState={CheckboxState[node.id] || false}
                                handleCheckBoxState={handleCheckBoxState}
                              />
                            ))}
                          </TreeView.Root>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default FileBrowser;
