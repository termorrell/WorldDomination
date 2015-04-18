var Territories = {
	Alaska: new Territory(0,'#Alaska','Alaska','Alaska.svg'),
	NorthWestTerritory: new Territory(1,'#NorthWestTerritory','North West Territory','NorthWestTerritory.svg'),
	Greenland: new Territory(2,'#Greenland','Greenland','Greenland.svg'),
	Alberta: new Territory(3,'#Alberta','Alberta','Alberta.svg'),
	Ontario: new Territory(4,'#Ontario','Ontario','Ontario.svg'),
	Quebec: new Territory(5,'#Quebec','Quebec','Quebec.svg'),
	WesternUnitedStates: new Territory(6,'#WesternUnitedStates','Western United States','WesternUnitedStates.svg'),
	EasternUnitedStates: new Territory(7,'#EasternUnitedStates','Eastern United States','EasternUnitedStates.svg'),
	CentralAmerica: new Territory(8,'#CentralAmerica','Central America','CentralAmerica.svg'),
	Venezuela: new Territory(9,'#Venezuela','Venezuela','Venezuela.svg'),
	Peru: new Territory(10,'#Peru','Peru','Peru.svg'),
	Brazil: new Territory(11,'#Brazil','Brazil','Brazil.svg'),
	Argentina: new Territory(12,'#Argentina','Argentina','Argentina.svg'),
	Iceland: new Territory(13,'#Iceland','Iceland','Iceland.svg'),
	Scandinavia: new Territory(14,'#Scandinavia','Scandinavia','Scandinavia.svg'),
	Ukraine: new Territory(15,'#Ukraine','Ukraine','Ukraine.svg'),
	GreatBritain: new Territory(16,'#GreatBritain','Great Britain','GreatBritain.svg'),
	NorthernEurope: new Territory(17,'#NorthernEurope','Northern Europe','NorthernEurope.svg'),
	WesternEurope: new Territory(18,'#WesternEurope','Western Europe','WesternEurope.svg'),
	SouthernEurope: new Territory(19,'#SouthernEurope','Southern Europe','SouthernEurope.svg'),
	NorthAfrica: new Territory(20,'#NorthAfrica','North Africa','NorthAfrica.svg'),
	Egypt: new Territory(21,'#Egypt','Egypt','Egypt.svg'),
	Congo: new Territory(22,'#Congo','Congo','Congo.svg'),
	EastAfrica: new Territory(23,'#EastAfrica','East Africa','EastAfrica.svg'),
	SouthAfrica: new Territory(24,'#SouthAfrica','South Africa','SouthAfrica.svg'),
	Madagascar: new Territory(25,'#Madagascar','Madagascar','Madagascar.svg'),
	Ural: new Territory(26,'#Ural','Ural','Ural.svg'),
	Siberia: new Territory(27,'#Siberia','Siberia','Siberia.svg'),
	Yakutsk: new Territory(28,'#Yakutsk','Yakutsk','Yakutsk.svg'),
	Kamchatka: new Territory(29,'#Kamchatka','Kamchatka','Kamchatka.svg'),
	Irkutsk: new Territory(30,'#Irkutsk','Irkutsk','Irkutsk.svg'),
	Mongolia: new Territory(31,'#Mongolia','Mongolia','Mongolia.svg'),
	Japan: new Territory(32,'#Japan','Japan','Japan.svg'),
	Afghanistan: new Territory(33,'#Afghanistan','Afghanistan','Afghanistan.svg'),
	MiddleEast: new Territory(34,'#MiddleEast','Middle East','MiddleEast.svg'),
	China: new Territory(35,'#China','China','China.svg'),
	India: new Territory(36,'#India','India','India.svg'),
	Siam: new Territory(37,'#Siam','Siam','Siam.svg'),
	Indonesia: new Territory(38,'#Indonesia','Indonesia','Indonesia.svg'),
	NewGuinea: new Territory(39,'#NewGuinea','New Guinea','NewGuinea.svg'),
	WesternAustralia: new Territory(40,'#WesternAustralia','Western Australia','WesternAustralia.svg'),
	EasternAustralia: new Territory(41,'#EasternAustralia','Eastern Australia','EasternAustralia.svg'),
};

function setNeighbouringTerritories() {

	// Alaska
	Territories.Alaska.neighbours = [Territories.NorthWestTerritory, Territories.Alberta, Territories.Kamchatka];

	// Northwest territory
	Territories.NorthWestTerritory.neighbours = [Territories.Alaska, Territories.Alberta, Territories.Ontario, Territories.Greenland];

	// Greenland
	Territories.Greenland.neighbours = [Territories.NorthWestTerritory, Territories.Ontario, Territories.Quebec, Territories.Iceland];

	// Alberta
	Territories.Alberta.neighbours = [Territories.Alaska, Territories.NorthWestTerritory, Territories.Ontario, Territories.WesternUnitedStates];

	// Ontario
	Territories.Ontario.neighbours = [Territories.NorthWestTerritory, Territories.Alberta, Territories.WesternUnitedStates, Territories.EasternUnitedStates, Territories.Quebec, Territories.Greenland];

	// Quebec
	Territories.Quebec.neighbours = [Territories.Ontario, Territories.EasternUnitedStates, Territories.Greenland];

	// Western United States
	Territories.WesternUnitedStates.neighbours = [Territories.Alberta, Territories.Ontario, Territories.EasternUnitedStates, Territories.CentralAmerica];

	// Eastern United States
	Territories.EasternUnitedStates.neighbours = [Territories.Quebec, Territories.Ontario, Territories.WesternUnitedStates, Territories.CentralAmerica];

	// Central America
	Territories.CentralAmerica.neighbours = [Territories.WesternUnitedStates, Territories.EasternUnitedStates, Territories.CentralAmerica];

	// Venezuela
	Territories.Venezuela.neighbours = [Territories.Brazil, Territories.Peru, Territories.CentralAmerica];

	// Peru
	Territories.Peru.neighbours = [Territories.Venezuela, Territories.Brazil, Territories.Argentina];

	// Brazil
	Territories.Brazil.neighbours = [Territories.Venezuela, Territories.Peru, Territories.Argentina, Territories.NorthAfrica];

	// Argentina
	Territories.Argentina.neighbours = [Territories.Peru, Territories.Brazil];

	// Iceland
	Territories.Iceland.neighbours = [Territories.Greenland, Territories.GreatBritain, Territories.Scandinavia];

	// Scandinavia
	Territories.Scandinavia.neighbours = [Territories.Iceland, Territories.GreatBritain, Territories.NorthernEurope, Territories.Ukraine];

	// Ukraine
	Territories.Ukraine.neighbours = [Territories.Scandinavia, Territories.NorthernEurope, Territories.SouthernEurope, Territories.MiddleEast, Territories.Afghanistan, Territories.Ural];

	// Great Britain
	Territories.GreatBritain.neighbours = [Territories.Iceland, Territories.Scandinavia, Territories.NorthernEurope, Territories.WesternEurope];

	// Northern Europe
	Territories.NorthernEurope.neighbours = [Territories.Scandinavia, Territories.GreatBritain, Territories.WesternEurope, Territories.SouthernEurope, Territories.Ukraine];

	// Western Europe
	Territories.WesternEurope.neighbours = [Territories.GreatBritain, Territories.NorthernEurope, Territories.SouthernEurope, Territories.NorthAfrica];

	// Southern Europe
	Territories.SouthernEurope.neighbours = [Territories.NorthernEurope, Territories.WesternEurope, Territories.NorthAfrica, Territories.Egypt, Territories.MiddleEast, Territories.Ukraine];

	// Northern Africa
	Territories.NorthAfrica.neighbours = [Territories.SouthernEurope, Territories.WesternEurope, Territories.Brazil, Territories.Congo, Territories.EastAfrica, Territories.Egypt];

	// Egypt
	Territories.Egypt.neighbours = [Territories.SouthernEurope, Territories.NorthAfrica, Territories.EastAfrica, Territories.MiddleEast];

	// Congo
	Territories.Congo.neighbours = [Territories.NorthAfrica, Territories.EastAfrica, Territories.SouthAfrica];

	// East Africa
	Territories.EastAfrica.neighbours = [Territories.Egypt, Territories.NorthAfrica, Territories.Congo, Territories.SouthAfrica, Territories.Madagascar, Territories.MiddleEast];

	// South Africa
	Territories.SouthAfrica.neighbours = [Territories.Congo, Territories.EastAfrica, Territories.Madagascar];

	// Madagaskar
	Territories.Madagascar.neighbours = [Territories.SouthAfrica, Territories.EastAfrica];

	// Ural
	Territories.Ural.neighbours = [Territories.Ukraine, Territories.Afghanistan, Territories.China, Territories.Siberia];

	// Siberia
	Territories.Siberia.neighbours = [Territories.Ural, Territories.China, Territories.Mongolia, Territories.Irkutsk, Territories.Yakutsk];

	// Yakutsk
	Territories.Yakutsk.neighbours = [Territories.Siberia, Territories.Irkutsk, Territories.Kamchatka];

	// Kamchatka
	Territories.Kamchatka.neighbours = [Territories.Yakutsk, Territories.Irkutsk, Territories.Mongolia, Territories.Alaska, Territories.Japan];

	// Irkutsk
	Territories.Irkutsk.neighbours = [Territories.Kamchatka, Territories.Yakutsk, Territories.Siberia, Territories.Mongolia];

	// Mongolia
	Territories.Mongolia.neighbours = [Territories.Kamchatka, Territories.Irkutsk, Territories.Siberia, Territories.China, Territories.Japan];

	// Japan
	Territories.Japan.neighbours = [Territories.Kamchatka, Territories.Mongolia];

	// Afghanistan
	Territories.Afghanistan.neighbours = [Territories.Ukraine, Territories.MiddleEast, Territories.India, Territories.China, Territories.Ural];

	// China
	Territories.China.neighbours = [Territories.Mongolia, Territories.Siberia, Territories.Ural, Territories.Afghanistan, Territories.India, Territories.Siam];

	// Middle East
	Territories.MiddleEast.neighbours = [Territories.Ukraine, Territories.SouthernEurope, Territories.Egypt, Territories.EastAfrica, Territories.India, Territories.Afghanistan];

	// India
	Territories.India.neighbours = [Territories.Afghanistan, Territories.MiddleEast, Territories.China, Territories.Siam];

	// Siam
	Territories.Siam.neighbours = [Territories.China, Territories.India, Territories.Indonesia];

	// Indonisia
	Territories.Indonesia.neighbours = [Territories.Siam, Territories.NewGuinea, Territories.WesternAustralia];

	// New Guinea
	Territories.NewGuinea.neighbours = [Territories.Indonesia, Territories.WesternAustralia, Territories.EasternAustralia];

	// Western Australia
	Territories.WesternAustralia.neighbours = [Territories.Indonesia, Territories.NewGuinea, Territories.EasternAustralia];

	// Eastern Australia
	Territories.EasternAustralia.neighbours = [Territories.NewGuinea, Territories.WesternAustralia];
}

function Territory(id, domID, name, svg) {
	this.id = id;
	this.domID = domID;
	this.name = name;
	this.player = null;
	this.armies = null;
	this.svg = svg;
	this.neighbours = [];
}

Territory.prototype = {
	constructor: Territory,

	setPlayer: function(owner) {
		this.owner = owner;
	},

	setArmies: function(armies) {
		this.armies = armies;
	},

	setNeighbours: function(neighbours) {
		this.neighbours = neighbours;
	}
}

var Colors = ['#3498db','#e74c3c','#f1c40f','#9b59b6','#1abc9c','#e67e22'];

var Players = [];

function Player(id, name, publicKey) {
	this.id = id;
	this.name = name;
	this.publicKey = publicKey;
	var numberOfPlayers = Players.length;
	this.color = Colors[numberOfPlayers];
}

Player.prototype = {

	constructor: Player,

	setColor: function(color) {
		this.color = color;
	}
}

var Cards = [];

function Card(territoryID, type) {
	this.territoryID = territoryID;
	this.type = type;
}

Card.prototype =  {

	constructor: Card
}