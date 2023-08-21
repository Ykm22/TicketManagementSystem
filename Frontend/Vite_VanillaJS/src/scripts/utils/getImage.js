export function getImage(venue_type){
    if(venue_type === 'Ballroom'){
      return 'src/assets/venues/ballroom.jpeg';
    }
    if(venue_type === 'Castle'){
      return 'src/assets/venues/castle.jpg';
    }
    if(venue_type === 'Conference Center'){
      return 'src/assets/venues/conference_center.jpg';
    }
    if(venue_type === 'Park'){
      return 'src/assets/venues/park.jpg';
    }
    if(venue_type === 'Stadion'){
      return 'src/assets/venues/stadium.jpg';
    }
    if(venue_type === 'Theater'){
      return 'src/assets/venues/theater.jpg';
    }
    if(venue_type === 'University'){
      return 'src/assets/venues/university.jpg';
    }
    return undefined;
  }