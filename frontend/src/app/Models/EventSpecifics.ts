import { Event } from 'src/app/Models/Event';
import { City } from "../city/city.component";

export interface EventSpecifics{
    id: string;
    date: string;
    events: Event | null;
    city: City | null;
}
