# Rappelkiste Specification

To start a test against a specific set of servers, please call

`gradle test -DbaseUrls="YOUR_LIST_OF_SERVERS_SEPARATED_BY_COMMA" --rerun-tasks`

For example:"

`gradle test -DbaseUrls="http://localhost:8080,http://localhost:8081" --rerun-tasks`