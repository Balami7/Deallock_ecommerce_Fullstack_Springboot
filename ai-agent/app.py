from fastapi import FastAPI, Header, HTTPException
from pydantic import BaseModel
import os

app = FastAPI()

class QueryRequest(BaseModel):
    prompt: str

class QueryResponse(BaseModel):
    text: str

AI_AGENT_API_KEY = os.getenv("AI_AGENT_API_KEY", "")

@app.post("/api/agent/query", response_model=QueryResponse)
def query(request: QueryRequest, x_ai_agent_key: str | None = Header(None)):
    if AI_AGENT_API_KEY and x_ai_agent_key != AI_AGENT_API_KEY:
        raise HTTPException(status_code=401, detail="Invalid or missing API key")

    # Replace this simple response with a real model integration later.
    return QueryResponse(text=f"AI agent received: {request.prompt}")
