import {EventSpecifics} from "./EventSpecifics";

export interface Event{
  id: string;
  createdby: string;
  eventSpecifics: EventSpecifics[];
  name: string;
  topic: string;
  description: string;
  imgUrl: string;
  maxParticipants: number;
}
