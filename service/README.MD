# Rappelkiste Service

To start an instance of Rappelkiste on localhost:8080 run:

`gradle startRappelkiste`

To start an instance of a Rappelcluster on several ports on localhost run:

`gradle startRappelCluster -Dports="PORTS_SEPARATED_BY_WHITESPACE"`

For Example:

`gradle startRappelCluster -Dports="8080 8081"`
