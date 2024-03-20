import React, { useState, useCallback } from "react";

let nodeName: String[] = [];

export function useNodeNames() {
  const setNodes = useCallback((updateNodeNames: String[]) => {
    nodeName = updateNodeNames;
  }, []);

  return { nodeName, setNodes };
}
