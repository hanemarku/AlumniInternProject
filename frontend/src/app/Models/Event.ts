import { EventSpecifics } from "./EventSpecifics";

export interface Event{
  id: string;
  name: string;
  topic: string;
  description: string;
  maxParticipants: number;
  imgUrl: string;
  eventSpecifics: EventSpecifics[];
  createdby: string;
}
