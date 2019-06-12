import googlemaps
from datetime import datetime

gmaps = googlemaps.Client(key="AIzaSyD0KibhlI-Ca_41h4SyUXVS8gaXni5zY-M")


'''
given an address returns its coordinates.

:param address: an address (like "1600 Amphitheatre Parkway, Mountain View, CA")
:type address: string

:rtype: a dictionary with two fields: "lat" and "lng"
'''
def get_coords_by_address(address):
    geocode_result = gmaps.geocode(address)
    return geocode_result[0]["geometry"]["location"]

print(get_coords_by_address("דיזינגוף 34 תל אביב, ישראל"))