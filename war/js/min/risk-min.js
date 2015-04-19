var Risk={Settings:{colors:{yellow:"#ff0",green:"#0f0",blue:"#00f",red:"#f00",purple:"#f0f",cyan:"#00ffe4"}},Territories:{},Continents:{},stage:null,backgroundLayer:null,mapLayer:null,topLayer:null,markersLayer:null,scale:1,init:function(){Risk.setUpContinentsObj(),Risk.setUpTerritoriesObj(),Kinetic.pixelRatio=2,Risk.stage=new Kinetic.Stage({container:"map",width:$(".main-content").width(),height:$(".main-content").height(),draggable:!0}),Risk.backgroundLayer=new Kinetic.Layer,Risk.overlayLayer=new Kinetic.Layer,Risk.topLayer=new Kinetic.Layer,Risk.markersLayer=new Kinetic.Layer,Risk.drawContinents(),Risk.drawTerritories(),Risk.stage.add(Risk.backgroundLayer),Risk.stage.add(Risk.overlayLayer),Risk.stage.add(Risk.topLayer),Risk.stage.add(Risk.markersLayer),Risk.backgroundLayer.draw(),Risk.overlayLayer.draw(),$(document).bind("mousewheel MozMousePixelScroll",function(e,i,t,a){e.preventDefault(),Risk.onMouseWheel(e,i,t,a)})},setUpContinentsObj:function(){for(id in ContinentPathData){var e=new Kinetic.Path({data:ContinentPathData[id].path,id:id});Risk.Continents[id]={name:id,path:e,color:ContinentPathData[id].fill,armyNum:null}}},setUpTerritoriesObj:function(){for(id in TerritoryNames){var e=new Kinetic.Path({data:TerritoryPathData[id].path,id:id});Risk.Territories[id]={name:TerritoryNames[id],path:e,color:null,armyNum:null}}},drawContinents:function(){for(c in Risk.Continents){var e=Risk.Continents[c].path;e.setFill(Risk.Continents[c].color),e.setOpacity(1),Risk.backgroundLayer.add(e)}},drawTerritories:function(){for(t in Risk.Territories){var e=Risk.Territories[t].path,i=new Kinetic.Group;i.add(e),Risk.overlayLayer.add(i),function(e,i,t){t.on("mouseover",function(){e.setFill("#eee"),e.setOpacity(.3),t.moveTo(Risk.topLayer),Risk.topLayer.draw(),$("#map").css("cursor","pointer")}),t.on("mouseout",function(){e.setFill("transparent"),e.setOpacity(0),t.moveTo(Risk.overlayLayer),Risk.topLayer.draw(),$("#map").css("cursor","move")}),t.on("click",function(i){console.log(e.attrs.id),location.hash=e.attrs.id,window.handleTerritoryClick([e.attrs.id]),Risk.fortifyTerritory(null,null,null,null)})}(e,t,i)}},fortifyTerritory:function(){console.log("Called");var e=new Kinetic.Path({data:pinData.path}),i=new Kinetic.Group;e.setX(Math.random()*$("#map").width()),e.setY(Math.random()*$("#map").height()),i.add(e),Risk.markersLayer.add(i),e.setFill("#000"),e.setOpacity(1),Risk.markersLayer.draw()},setCurrentPlayer:function(){},updateMap:function(e){},makeTurn:function(){},allocateArmies:function(){},onMouseWheel:function(e){var i;i=e.originalEvent.detail?e.originalEvent.detail:e.originalEvent.wheelDelta,0!==i&&e.originalEvent.preventDefault();var t;t=i>0?Risk.scale+Math.abs(i/640):Risk.scale-Math.abs(i/640),Risk.scale=t,console.log(t),Risk.backgroundLayer.setScale(t),Risk.backgroundLayer.draw()}};