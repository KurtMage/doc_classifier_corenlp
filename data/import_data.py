import predictionio
import argparse
import json


def sendData(args):
    # A user rates an item
    USER_ID = ""
    ITEM_ID = ""
    client = predictionio.EventClient(
        access_key=args.access_key,
        url=args.url,
        threads=5,
        qsize=500
    )
    file = open(args.file, "r")
    # with open(args.file) as data_file:
    #     data = json.load(data_file)
    # pprint(data)

    buildingString = False
    jsonString = ""
    jsonObjects = []
    for line in file:
        # start building the string
        if line.startswith("{"):
            if buildingString:
                raise RuntimeError("a line started with { and we were already building string")
            jsonString += " " + line.strip()
            buildingString = True
            
        elif buildingString:
            jsonString += " " + line.strip()
            # we've finished concatenating, loads() it into our collection
            if line.startswith("}"):
                buildingString = False
                # add the json object to our list of objects
                jsonObjects.append(json.loads(jsonString))
                jsonString = ""
    if buildingString:
        raise RuntimeError("a string did not finish building by the end of file")


    # now go through the objects and for each interest, create an event
    counter = 0
    for jsonObject in jsonObjects:
        message = jsonObject["message"]

        if message is not None:
            for interest in jsonObject["adgroup"]["targeting"]["interests"]:
                interestName = interest["name"]
                counter += 1

                response = client.create_event(
                    event="$set",
                    entity_type="phrase",
                    entity_id=counter,
                    properties= { "phrase" : message,
                                  "Interest" : interestName
                    }
                )
                print response
                print(counter)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
    description="Import sample data for classification engine")
  parser.add_argument('--access_key', default='invald_access_key')
  parser.add_argument('--url', default="http://localhost:7070")
  parser.add_argument('--file', default="cleanAdHuskyData300.json")

  args = parser.parse_args()
  print args
  sendData(args)
"""
client = predictionio.EventClient(
    access_key=<ACCESS KEY>,
    url=<URL OF EVENTSERVER>,
    threads=5,
    qsize=500
)
"""