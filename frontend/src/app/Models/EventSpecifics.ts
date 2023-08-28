import { City } from "../city/city.component";
import { Event } from "./Event";

export interface EventSpecifics{
    id: string;
    date: string;
    event: Event | null;
    city: City | null;
}
