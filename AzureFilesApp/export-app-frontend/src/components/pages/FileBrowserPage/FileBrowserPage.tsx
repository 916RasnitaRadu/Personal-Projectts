import {
  Routes as Router,
  Route,
  Navigate,
  Outlet,
  useParams,
} from "react-router-dom";

import FileBrowser from "../../molecules/FileBrowser";

const FileBrowserPage = () => {
  const { bucketTitle } = useParams();
  const convertedBucketTitle = bucketTitle ?? "";
  return (
    <div className="h-screen bg-white">
      <FileBrowser bucketName={convertedBucketTitle} />
    </div>
  );
};

export default FileBrowserPage;
