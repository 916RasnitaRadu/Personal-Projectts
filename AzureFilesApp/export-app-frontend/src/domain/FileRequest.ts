import { Bucket } from "./bucket";

export interface FileRequest {
  id: Number;
  name: String;
  type: String;
  BucketEntity: Bucket;
}
