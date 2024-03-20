import ICard from "./ICard";
import { useEffect, useState } from "react";
import { getBuckets } from "../../../services/BucketService";
import {
  useNotificationError,
  useNotificationSuccess,
} from "src/components/NotificationComponent";
import { response } from "express";
import Folder from "../Icons/Folder";

interface CardsProps {
  retrieveBucketState: Boolean;
}

const Cards = ({ retrieveBucketState }: CardsProps) => {
  const [bucketsArray, setBucketsArray] = useState<ICard[]>([]);
  const showSuccessNotification = useNotificationSuccess();
  const showErrorNotification = useNotificationError();

  const retrieveBuckets = async () => {
    try {
      const response = await getBuckets();
      if (response) {
        const { data: fetchedBuckets, message } = response;
        const transformedData: ICard[] = fetchedBuckets.map((bucket) => ({
          title: bucket.name,
          description: bucket.description,
        }));

        setBucketsArray(transformedData);
        showSuccessNotification(message);
      }
    } catch (error: any) {
      showErrorNotification(error.response.data.message);
    }
  };

  useEffect(() => {
    if (retrieveBucketState) {
      retrieveBuckets();
    }
  }, [retrieveBucketState]);

  return (
    <div className="flex flex-wrap items-center justify-center h-screen mt-2">
      {bucketsArray.map((bucket, index) => (
        <div
          key={index}
          className="w-3/4 md:w-[48%] lg:w-1/3 xl:w-[27%] 2xl:w-[21%] custom-range"
        >
          <a
            href={`/fileBrowser/${bucket.title}`}
            className="block max-w-xs h-52 m-6 p-5 rounded-lg bg-[#343433] hover:scale-105"
          >
            <div className="flex justify-center">
              <Folder />
              <h5 className="pl-2 mt-2 mb-3 text-2xl font-bold tracking-wide text-[#F7F2F1] text-center">
                {bucket.title}
              </h5>
            </div>
            <p className="font-normal text-[#F7F2F1] pt-2">
              {bucket.description}
            </p>
          </a>
        </div>
      ))}
    </div>
  );
};

export default Cards;
