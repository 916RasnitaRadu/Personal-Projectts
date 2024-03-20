import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import { getBuckets } from "../../src/services/BucketService";

const mockAxios = new MockAdapter(axios);

describe("getBuckets function", () => {
  beforeEach(() => {
    mockAxios.reset();
  });

  it("return buckets", async () => {
    const mockData = [{ name: "Bucket 1", description: null }];
    mockAxios.onGet("http://localhost:8080/buckets").reply(200, mockData);

    const result = await getBuckets();
    expect(result).toEqual(mockData);
  });

  it("handle errors", async () => {
    mockAxios
      .onGet("http://localhost:8080/buckets")
      .reply(500, "Internal Server Error");

    try {
      await getBuckets();
      expect(true).toBe(false);
    } catch (error: any) {
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe("Request failed with status code 500");
    }
  });
});
