<template>
    <div class="clickableTile">
        <construction-tile v-if="buildingProperties.isUnderConstruction"></construction-tile>
        <img v-else class="tileImg" :src="getTileSource()"/>
    </div>
</template>

<script>
    export default{
        props: ['buildingProperties'],
       computed:{
            seasonsOn: function () {
                return this.$store.state.seasonsEnabled
            },
            currentSeason: function () {
                return this.$store.state.currentSeason
            }
        },
        methods:{
            getTileSource: function () {

                if (this.seasonsOn && this.currentSeason === 'winter'){
                    if (this.isLevelTen()){
                        return require('../../assets/tiles/level_10/winter/mine_10.png')
                    }
                    return require('../../assets/winterTiles/mine.png')
                }else{
                    if (this.isLevelTen()){
                        return require('../../assets/tiles/level_10/mine_10.png')
                    }
                    return require('../../assets/tiles/mine.png')
                }
            },
            isLevelTen: function () {
                if (this.buildingProperties.level >= 10){
                    return true;
                }
                return false;
            }
        }
    }
</script>

<style>


</style>
